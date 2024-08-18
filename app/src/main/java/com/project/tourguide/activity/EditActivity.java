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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.tourguide.R;
import com.project.tourguide.databinding.ActivityEditBinding;
import com.project.tourguide.model.MainModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT = "EDIT";
    private ActivityEditBinding binding;
    private String dp;
    private MainModel model;
    private static final int REQUEST_FROM_GALLERY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ///data dari kelas model terkait data kamera di panggil di activity ini, kemudian data tersebut di tampilkan pada halaman ini
        model = getIntent().getParcelableExtra(EXTRA_EDIT);

        Glide.with(this)
                .load(model.getImage())
                .into(binding.ArticleDp);

        binding.judulEt.setText(model.getJudul());
        binding.deskripsiEt.setText(model.getDeskripsi());
        binding.statusEt.setText(model.getKategori());
        binding.statusEt.setEnabled(false);


        // KLIK Perbarui gambar
        binding.imageHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditActivity.this)
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(REQUEST_FROM_GALLERY);
            }
        });


        // KLIK UNGGAH data kamera
        binding.uploadArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKonten();
            }
        });
    }

    private void updateKonten() {
        String judul = binding.judulEt.getText().toString().trim();
        String deskripsi = binding.deskripsiEt.getText().toString().trim();
        String kategori = binding.statusEt.getText().toString().trim();

        if(judul.isEmpty()) {
            Toast.makeText(EditActivity.this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(deskripsi.isEmpty()) {
            Toast.makeText(EditActivity.this, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        // SIMPAN DATA PERALATAN KAMERA KE DATABASE
        Map<String, Object> product = new HashMap<>();
        product.put("judul", judul);
        product.put("description", deskripsi);
        product.put("kategori", kategori);
        if(dp != null) {
            product.put("dp", dp);
        }
        FirebaseFirestore
                .getInstance()
                .collection("konten")
                .document(model.getUid())
                .update(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            showSuccessDialog();
                            onBackPressed();
                            onBackPressed();
                        }
                        else {
                            binding.progressBar.setVisibility(View.GONE);
                            showFailureDialog();
                            onBackPressed();
                        }
                    }
                });

    }
    /// tampilkan dialog box ketika gagal mengupload
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Gagal Mengunggah Konten")
                .setMessage("Terdapat kesalahan ketika mengunggah kamera, silahkan periksa koneksi internet anda, dan coba lagi nanti")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    /// tampilkan dialog box ketika sukses mengupload
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Berhasil Mengunggah Konten")
                .setMessage("Konten akan segera terbit, anda dapat mengedit atau menghapus kamera jika terdapat kesalahan")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
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
                                    Toast.makeText(EditActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
                                    Log.d("imageDp: ", e.toString());
                                }))
                .addOnFailureListener(e -> {
                    mProgressDialog.dismiss();
                    Toast.makeText(EditActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
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