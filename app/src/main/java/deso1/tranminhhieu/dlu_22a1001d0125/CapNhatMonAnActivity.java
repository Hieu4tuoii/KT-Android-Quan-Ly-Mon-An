package deso1.tranminhhieu.dlu_22a1001d0125;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import deso1.tranminhhieu.dlu_22a1001d0125.Model.MonAn;

public class CapNhatMonAnActivity extends AppCompatActivity {
    private EditText txtTenMonAn, txtGiaTien, txtDonViTinh, txtHinhAnh;
    private Button btnLuu, btnXoa,  btnSelectImage;
    private MonAn monAn;
    private ImageView imgSelected;
    private String imagePath = "";

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                            imgSelected.setImageBitmap(bitmap);
                            imagePath = saveImageToInternalStorage(bitmap);
                            txtHinhAnh.setText(imagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Không thể tải ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cap_nhat_mon_an);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();
        nhanDuLieuTuIntentVaCapNhatGiaoDien();

        //xu ly khi nhan nut luu
        btnLuu.setOnClickListener(v -> capNhatMonAn());

        //xu ly khi nhan nut xoa
        btnXoa.setOnClickListener(v -> xoaMonAn());

        // Xử lý khi nhấn nút chọn ảnh
        btnSelectImage.setOnClickListener(v -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }


    private void xoaMonAn() {
        btnXoa.setOnClickListener(v -> {
    new AlertDialog.Builder(this)
        .setTitle("Xác nhận xóa")
        .setMessage("Bạn có chắc chắn muốn xóa món ăn này không?")
        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
            MainActivity.db.deleteMonAnById(monAn.getId());
            Toast.makeText(this, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CapNhatMonAnActivity.this, MainActivity.class));
        })
        .setNegativeButton(android.R.string.no, null)
        .show();
});}

    private void capNhatMonAn(){
        //kiem tra du lieu nhap vao
        if(txtTenMonAn.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtGiaTien.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtDonViTinh.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đơn vị tính", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtHinhAnh.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập hình ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        //update lai mon an theo du lieu tu giao dien
        monAn.setTenMonAn(txtTenMonAn.getText().toString());
        monAn.setGiaTien(Float.parseFloat(txtGiaTien.getText().toString()));
        monAn.setDonViTinh(txtDonViTinh.getText().toString());
        monAn.setHinhAnh(txtHinhAnh.getText().toString());
        //cap nhat lai mon an
        MainActivity.db.updateMonAn(this.monAn);
        Toast.makeText(this, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CapNhatMonAnActivity.this, MainActivity.class));
    }

    private void AnhXa(){
        txtTenMonAn = findViewById(R.id.txtTenMonAn);
        txtGiaTien = findViewById(R.id.txtGia);
        txtDonViTinh = findViewById(R.id.txtDonViTinh);
        txtHinhAnh = findViewById(R.id.txtHinhAnh);
        btnLuu = findViewById(R.id.btnLuu);
        btnXoa = findViewById(R.id.btnXoa);
        imgSelected = findViewById(R.id.imgSelected);
        btnSelectImage = findViewById(R.id.btnSelectImage);
    }

    private void nhanDuLieuTuIntentVaCapNhatGiaoDien(){
        //nhan doi tuong mon an tu intent
        Intent intent = getIntent();
        monAn = (MonAn) intent.getSerializableExtra("monAn");
        //cap nhat du lieu len giao dien
        txtTenMonAn.setText(monAn.getTenMonAn());
        txtGiaTien.setText(monAn.getGiaTien()+"");
        txtDonViTinh.setText(monAn.getDonViTinh());
        txtHinhAnh.setText(monAn.getHinhAnh());
        // Load image from the saved path
        if (monAn.getHinhAnh() != null && !monAn.getHinhAnh().isEmpty()) {
            File imageFile = new File(monAn.getHinhAnh());
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                imgSelected.setImageBitmap(bitmap);
            }
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        // Create a unique filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";

        // Get the directory for app's private files
        File directory = getFilesDir();
        File imageFile = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}