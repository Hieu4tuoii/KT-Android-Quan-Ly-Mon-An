package deso1.tranminhhieu.dlu_22a1001d0125.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import deso1.tranminhhieu.dlu_22a1001d0125.Model.MonAn;

public class database extends SQLiteOpenHelper {
    public database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void QueryData(String Sql){
        SQLiteDatabase database = getWritableDatabase();//lay quyen chinh sua data base
        database.execSQL(Sql);//thuc thi cau lenh sql duoc truyen vao
    }

    //ham inset du lieu vao danh muc
    public void InsertMonAn(String tenMonAn, Float giaTien, String donViTinh, String hinhAnh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO MonAn VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();//xóa các chỉ số bindings cũ
        statement.bindString(1, tenMonAn);
        statement.bindDouble(2, giaTien);
        statement.bindString(3, donViTinh);
        statement.bindString(4, hinhAnh);
        statement.executeInsert();
    }

    //get ds mon an
    public List<MonAn> getDsMonAn(){
        List<MonAn> listMonAn = new ArrayList<>();
        //lay du lieu tu db
        Cursor cursor = GET_DATA("SELECT * FROM MonAn");
        while(cursor.moveToNext()){//doc lien tuc cac hang cua bang den khi nao khong doc duoc du lieu nua thi dung
            MonAn monAn =new MonAn(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getFloat(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            listMonAn.add(monAn);
        }
        return listMonAn;
    }

    public void deleteMonAnById(int id){
        QueryData("DELETE FROM MonAn WHERE id = "+id);
    }

    public void updateMonAn(MonAn monAn){
        QueryData("UPDATE MonAn SET tenMonAn = '"+monAn.getTenMonAn()+"', giaTien = "+monAn.getGiaTien()+", donViTinh = '"+monAn.getDonViTinh()+"', hinhAnh = '"+monAn.getHinhAnh()+"' WHERE id = "+monAn.getId());
    }

    //lay du lieu tu db
    public Cursor GET_DATA(String sql){
        SQLiteDatabase database = getReadableDatabase();//lay quyen doc db
        return database.rawQuery(sql, null);//selectionArgs de thay the cac tham so ? trong cau lenh sql
    }
}
