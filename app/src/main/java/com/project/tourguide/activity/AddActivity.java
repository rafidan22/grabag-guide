package com.project.tourguide.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.tourguide.R;
import com.project.tourguide.databinding.ActivityAddBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding binding;
    private String status = "";
    private String dp;
    private static final int REQUEST_FROM_GALLERY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // unggah peralatan kamera
        binding.uploadArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadKonten();
            }
        });

        // KLIK TAMBAH GAMBAR
        binding.imageHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddActivity.this)
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(REQUEST_FROM_GALLERY);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipe, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.statusEt.setAdapter(adapter);
        binding.statusEt.setOnItemClickListener((adapterView, view, i, l) -> {
            status = binding.statusEt.getText().toString();
        });
    }

    private void uploadKonten() {
        String judul = binding.judulEt.getText().toString().trim();
        String deskripsi = binding.deskripsiEt.getText().toString().trim();
        String kategori = binding.statusEt.getText().toString().trim();


        if(judul.isEmpty()) {
            Toast.makeText(AddActivity.this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(deskripsi.isEmpty()) {
            Toast.makeText(AddActivity.this, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(kategori.isEmpty()) {
            Toast.makeText(AddActivity.this, "Kategori Konten harus dipilih", Toast.LENGTH_SHORT).show();
            return;
        }else if(dp == null) {
            Toast.makeText(AddActivity.this, "Gambar tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        String uid = String.valueOf(System.currentTimeMillis());
        Map<String, Object> product = new HashMap<>();
        product.put("judul", judul);
        product.put("description", deskripsi);
        product.put("uid", uid);
        product.put("kategori", kategori);
        product.put("dp", dp);
        FirebaseFirestore
                .getInstance()
                .collection("konten")
                .document(uid)
                .set(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            showSuccessDialog();
                        }
                        else {
                            binding.progressBar.setVisibility(View.GONE);
                            showFailureDialog();
                        }
                    }
                });
    }


    /// tampilkan dialog box ketika gagal mengupload
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Gagal Mengunggah Kamera")
                .setMessage("Terdapat kesalahan ketika mengunggah konten, silahkan periksa koneksi internet anda, dan coba lagi nanti")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                })
                .show();
    }

    /// tampilkan dialog box ketika sukses mengupload
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Berhasil Mengunggah Konten")
                .setMessage("Kamera akan segera terbit, anda dapat mengedit atau menghapus konten jika terdapat kesalahan")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                });
    }


        /// fungsi untuk memvalidasi kode berdasarkan inisiasi variabel di atas tadi
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_FROM_GALLERY) {
                uploadArticleDp(data.getData());
            }
        }
    }


    /// fungsi untuk mengupload foto kedalam cloud storage
    private void uploadArticleDp(Uri data) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        String imageFileName = "konten/data_" + System.currentTimeMillis() + ".png";

        mStorageRef
                .child(imageFileName)
                .putFile(data)
                .addOnSuccessListener(taskSnapshot ->
                        mStorageRef
                                .child(imageFileName)
                                .getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    mProgressDialog.dismiss();
                                    dp = uri.toString();
                                    binding.imageHint.setVisibility(View.GONE);
                                    Glide
                                            .with(this)
                                            .load(dp)
                                            .into(binding.ArticleDp);
                                })
                                .addOnFailureListener(e -> {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(AddActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
                                    Log.d("imageDp: ", e.toString());
                                }))
                .addOnFailureListener(e -> {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
                    Log.d("imageDp: ", e.toString());
                });
    }

    /// HAPUSKAN ACTIVITY KETIKA SUDAH TIDAK DIGUNAKAN, AGAR MENGURANGI RISIKO MEMORY LEAKS
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}