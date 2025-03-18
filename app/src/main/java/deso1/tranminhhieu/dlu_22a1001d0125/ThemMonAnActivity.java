package deso1.tranminhhieu.dlu_22a1001d0125;
        import android.content.Intent;
        import android.graphics.Bitmap;
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

        public class ThemMonAnActivity extends AppCompatActivity {
            private EditText txtTenMonAn, txtGiaTien, txtDonViTinh, txtHinhAnh;
            private Button btnLuu, btnSelectImage;
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
                setContentView(R.layout.activity_them_mon_an);
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

                AnhXa();

                // Xử lý khi nhấn nút chọn ảnh
                btnSelectImage.setOnClickListener(v -> openImagePicker());

                // Xử lý khi nhấn nút lưu
                btnLuu.setOnClickListener(v -> ThemMonAn());
            }

            private void openImagePicker() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imagePickerLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
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

            private void ThemMonAn() {
                // Kiểm tra dữ liệu nhập vào cho từng trường dữ liệu
                if (txtTenMonAn.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtGiaTien.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập giá tiền", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtDonViTinh.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đơn vị tính", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imagePath.isEmpty()) {
                    Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm món ăn vào db
                String tenMonAn = txtTenMonAn.getText().toString();
                float giaTien = Float.parseFloat(txtGiaTien.getText().toString());
                String donViTinh = txtDonViTinh.getText().toString();
                MainActivity.db.InsertMonAn(tenMonAn, giaTien, donViTinh, imagePath);
                Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThemMonAnActivity.this, MainActivity.class));
            }

            private void AnhXa() {
                txtTenMonAn = findViewById(R.id.txtTenMonAn);
                txtGiaTien = findViewById(R.id.txtGia);
                txtDonViTinh = findViewById(R.id.txtDonViTinh);
                txtHinhAnh = findViewById(R.id.txtHinhAnh);
                btnLuu = findViewById(R.id.btnLuu);
                btnSelectImage = findViewById(R.id.btnSelectImage);
                imgSelected = findViewById(R.id.imgSelected);
            }
        }