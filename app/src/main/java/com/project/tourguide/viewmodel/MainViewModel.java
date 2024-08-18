package com.project.tourguide.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.tourguide.model.MainModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MainModel>> listKonten = new MutableLiveData<>();
    final ArrayList<MainModel> kontenModelArrayList = new ArrayList<>();
    private static final String TAG = MainViewModel.class.getSimpleName();

    public void setEatList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Wisata Alam")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setFunList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Wisata Sejarah")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setKulinerList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Wisata Budaya")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setPasarList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Taman")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setPenginapanList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Pasar")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setReligiList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Penginapan")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setShoppingList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Wisata Kuliner")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setTamanList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","Wisata Religi")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setVisitList() {
        kontenModelArrayList.clear();
        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("konten")
                    .whereEqualTo("kategori","About")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                MainModel model = new MainModel();

                                model.setUid("" + document.get("uid"));
                                model.setJudul("" + document.get("judul"));
                                model.setDeskripsi("" + document.get("description"));
                                model.setImage("" + document.get("dp"));
                                model.setKategori("" + document.get("kategori"));

                                kontenModelArrayList.add(model);
                            }
                            listKonten.postValue(kontenModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public LiveData<ArrayList<MainModel>> getKontenList() {
        return listKonten;
    }

}
