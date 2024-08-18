package com.project.tourguide.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.tourguide.R;
import com.project.tourguide.databinding.ActivityDetailBinding;
import com.project.tourguide.model.MainModel;

import org.jetbrains.annotations.NotNull;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_KONTEN = "konten";
    private ActivityDetailBinding binding;
    private MainModel model;


    @Override
    protected void onResume() {
        super.onResume();

        getKonten();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       getKonten();

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_EDIT, model);
                startActivity(intent);
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();
            }
        });
    }

    private void getKonten() {
        model = getIntent().getParcelableExtra(EXTRA_KONTEN);
        Glide.with(this)
                .load(model.getImage())
                .into(binding.dp);

        binding.tvtitle.setText(model.getJudul());
        binding.kategori.setText("Kategori: " + model.getKategori());
        binding.tvdeskripsi.setText(model.getDeskripsi());
    }

    /// konfirmasi menbghapus data kamera
    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi menghapus konten")
                .setMessage("Apakah anda yakin ingin menghapus konten ini ?")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setPositiveButton("YAKIN", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    deleteCamera();
                })
                .setNegativeButton("TIDAK", (dialog, i) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void deleteCamera() {
        ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        FirebaseFirestore
                .getInstance()
                .collection("konten")
                .document(model.getUid())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Toast.makeText(DetailActivity.this, "Berhasil menghapus konten", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(DetailActivity.this, "Gagal menghapus konten", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }