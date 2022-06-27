package com.midterm.english_app.business.provider

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.midterm.english_app.model.AccountModel
import com.midterm.english_app.model.FavouriteModel
import com.midterm.english_app.view.App
import com.midterm.english_app.view.MainActivity
import javax.inject.Inject

class FirebaseDatabaseManager {

    @Inject
    constructor() {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var firebaseDatabaseProvider: FirebaseDatabaseProvider
    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var mainActivity: MainActivity
    private var insertedCount = 0
    private var insertError = false

    fun insertAccount(accountModel: AccountModel) {
        firebaseDatabaseProvider.accountsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace().toString()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var exists = false
                    for (child in p0.children) {
                        val data = child.getValue(AccountModel::class.java)
                        if (accountModel.email == data?.email) {
                            exists = true
                            val newAccountModel = AccountModel(data.email, data.accID, data.type, data.description)
                            databaseManager.insertAccount(newAccountModel)
                            Log.d(
                                "Account Info",
                                "${data?.accID} ${data?.email} ${data?.type} ${data?.description}"
                            )
                            break
                        }
                    }
                    Log.d("Account Exists", exists.toString())
                    if (!exists) {
                        firebaseDatabaseProvider.accountsRef.push().setValue(accountModel)
                            .addOnSuccessListener {
                                databaseManager.insertAccount(accountModel)
                            }
                            .addOnFailureListener {
                                it.printStackTrace()
                            }
                    }
                    databaseManager.deleteAllVocabulary()
                }
            })
    }

    fun sync() {
        object : Thread() {
            @Override
            override fun run() {
                // First, upload
                insertError = false
                val favouritesNotSynced = databaseManager.getFavoritesNotSynced()
                for (item in favouritesNotSynced)
                    insertFavourite(item)

                // Second, download
                if (favouritesNotSynced.size > 0) {
                    while (insertedCount < favouritesNotSynced.size && !insertError)
                        sleep(1000)
                    getFavourites()
                } else
                    getFavourites()
                join()
            }

        }.start()
    }

    private fun getFavourites() {
        firebaseDatabaseProvider.accountsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    mainActivity.favouriteFragment.onRefreshed()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var exists = false
                    val accId = databaseManager.getAccID()
                    val email = databaseManager.getEmail()
                    if (email != null && accId != null) {
                        for (child in p0.children) {
                            val data = child.getValue(AccountModel::class.java)
                            if (email == data?.email) {
                                exists = true
                                Log.d(
                                    "Account Info",
                                    "${data?.accID} ${data?.email} ${data?.type} ${data?.description}"
                                )
                                break
                            }
                        }

                        Log.d("Account Exists", exists.toString())
                        if (exists) {
                            firebaseDatabaseProvider.favouritesRef
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {
                                        mainActivity.favouriteFragment.onRefreshed()
                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        object : Thread() {
                                            override fun run() {
                                                val favourite = ArrayList<FavouriteModel.Item>()
                                                for (child in p0.children) {
                                                    val data = child.getValue(FavouriteModel.Item::class.java)
                                                    if (data?.accID == accId)
                                                        favourite.add(data)
                                                }
                                                if (favourite.isNotEmpty()) {
                                                    databaseManager.deleteAllVocabulary()
                                                    for (element in favourite) {
                                                        Log.d(
                                                            "Favourites Info",
                                                            "${element?.accID} ${element?.vocabulary} ${element?.vi} ${element?.description}"
                                                        )
                                                        element.synced = "synced"
                                                        databaseManager.insertVocabulary(element)
                                                    }
                                                } else {
                                                    Log.d(
                                                        "Favourites Info",
                                                        "Empty"
                                                    )
                                                    databaseManager.deleteAllVocabulary()
                                                }
                                                mainActivity.favouriteFragment.onRefreshed()
                                                mainActivity.favouriteFragment.message(("Synced"))
                                                join()
                                            }
                                        }.start()
                                    }
                                })
                        } else {
                            mainActivity.favouriteFragment.onRefreshed()
                        }
                    }
                }
            })

    }

    fun insertFavourite(favouriteModel: FavouriteModel.Item) {
        firebaseDatabaseProvider.accountsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var exists = false
                    val accId = databaseManager.getAccID()
                    val email = databaseManager.getEmail()
                    if (email != null && accId != null) {
                        for (child in p0.children) {
                            val data = child.getValue(AccountModel::class.java)
                            if (email == data?.email) {
                                exists = true
                                Log.d(
                                    "Account Info",
                                    "${data?.accID} ${data?.email} ${data?.type} ${data?.description}"
                                )
                                break
                            }
                        }

                        Log.d("Account Exists", exists.toString())
                        if (exists) {
                            firebaseDatabaseProvider.favouritesRef
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        var vocabularyExists = false
                                        for (child in p0.children) {
                                            val data = child.getValue(FavouriteModel.Item::class.java)
                                            if (data?.accID == accId && data?.vocabulary == favouriteModel.vocabulary) {
                                                vocabularyExists = true
                                                break
                                            }
                                        }

                                        if (!vocabularyExists) {
                                            val newFavouriteModel = FavouriteModel.Item(
                                                null,
                                                databaseManager.getAccID(),
                                                favouriteModel.vocabulary,
                                                favouriteModel.vi,
                                                ""
                                            )
                                            firebaseDatabaseProvider.favouritesRef.push()
                                                .setValue(newFavouriteModel)
                                                .addOnSuccessListener {
                                                    databaseManager.updateVocabularySyncStateName(newFavouriteModel.vocabulary, true)
                                                    Log.d("Synced on cloud", newFavouriteModel.vocabulary)
                                                }
                                                .addOnFailureListener {
                                                    Log.d("Cannot sync on cloud", newFavouriteModel.vocabulary)
                                                    insertError = true
                                                }
                                        } else {
                                            Log.d("Really on cloud", favouriteModel.vocabulary)
                                        }
                                        insertedCount++
                                    }
                                })
                        }
                    }
                }
            })
    }

    fun deleteFavouriteByVocabulary(vocabulary: String) {
        firebaseDatabaseProvider.accountsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var exists = false
                    val accId = databaseManager.getAccID()
                    val email = databaseManager.getEmail()
                    if (email != null && accId != null) {
                        for (child in p0.children) {
                            val data = child.getValue(AccountModel::class.java)
                            if (email == data?.email) {
                                exists = true
                                Log.d(
                                    "Account Info",
                                    "${data?.accID} ${data?.email} ${data?.type} ${data?.description}"
                                )
                                break
                            }
                        }

                        Log.d("Account Exists", exists.toString())
                        if (exists) {
                            firebaseDatabaseProvider.favouritesRef
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        for (child in p0.children) {
                                            val data = child.getValue(FavouriteModel.Item::class.java)
                                            if (data?.accID == accId && data?.vocabulary == vocabulary) {
                                                Log.d("Will delete", vocabulary)
                                                child.ref.removeValue()
                                                    .addOnSuccessListener {
                                                        Log.d("Deleted on cloud", vocabulary)
                                                    }
                                                    .addOnFailureListener {
                                                        Log.d("Cannot deleted on cloud", vocabulary)
                                                    }
                                                break
                                            } else
                                                Log.d("Cannot find $vocabulary on cloud", vocabulary)
                                        }

                                    }
                                })
                        }
                    }
                }
            })
    }
}