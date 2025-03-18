package deso1.tranminhhieu.dlu_22a1001d0125;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import deso1.tranminhhieu.dlu_22a1001d0125.Adapter.Listener.IItemMonAnListener;
import deso1.tranminhhieu.dlu_22a1001d0125.Adapter.MonAnAdapter;
import deso1.tranminhhieu.dlu_22a1001d0125.Database.database;
import deso1.tranminhhieu.dlu_22a1001d0125.Model.MonAn;

public class MainActivity extends AppCompatActivity {
    public static database db;//chuyen thanh static de ben man hinh khac co the goi duoc
    private ImageButton ibtnThemMonAn;
    private RecyclerView rvDsMonAn;
    private MonAnAdapter monAnAdapter;
    private List<MonAn> listMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        KhoiTaoDB();
        AnhXa();
        //xu ly khi nhan vao nut them mon an
        ibtnThemMonAn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThemMonAnActivity.class));
        });
        hienDsMonAn();
    }

    //khoi tao db
    private void KhoiTaoDB(){
        db = new database(this, "baikiemtra18032025.sqlite", null, 1);
        //tao bang mon an
        db.QueryData("CREATE TABLE IF NOT EXISTS MonAn(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenMonAn VARCHAR(150), " +
                " giaTien REAL, " +
                " donViTinh VARCHAR(150)," +
                " hinhAnh VARCHAR(150))");
    }

    private void AnhXa(){
        ibtnThemMonAn = findViewById(R.id.ibtnThemMonAn);
        rvDsMonAn = findViewById(R.id.recyclerDsMonAn);
    }

    private void hienDsMonAn(){
        listMonAn = MainActivity.db.getDsMonAn();
        monAnAdapter = new MonAnAdapter(listMonAn, new IItemMonAnListener() {
            @Override
            public void onClickUpdateButton(MonAn monAn) {
                //chuyen huong sang trang cap nhat mon an
                Intent intent = new Intent(MainActivity.this, CapNhatMonAnActivity.class);
                intent.putExtra("monAn", monAn);
                startActivity(intent);
            }
        });
        rvDsMonAn.setAdapter(monAnAdapter);
     LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
rvDsMonAn.setLayoutManager(linearLayoutManager);
    }
}