package deso1.tranminhhieu.dlu_22a1001d0125.Adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import deso1.tranminhhieu.dlu_22a1001d0125.Adapter.Listener.IItemMonAnListener;
import deso1.tranminhhieu.dlu_22a1001d0125.Model.MonAn;
import deso1.tranminhhieu.dlu_22a1001d0125.R;


public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnHolder> {
    private List<MonAn> dsMonAn;
    private IItemMonAnListener itemMonAnListener;
    public MonAnAdapter(List<MonAn> dsMonAn, IItemMonAnListener itemMonAnListener) {
        this.dsMonAn = dsMonAn;
        this.itemMonAnListener = itemMonAnListener;
    }
    @NonNull
    @Override
    public MonAnAdapter.MonAnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan, parent, false);
        return new MonAnAdapter.MonAnHolder(view);//tạo ra viewholder dựa theo các itemview truyền vào danhMucHolder
    }
    @Override
    public void onBindViewHolder(@NonNull MonAnAdapter.MonAnHolder holder, int position) {
        MonAn monAn = dsMonAn.get(position);
        if (monAn == null) return;
        else {
            //set thông tin cho item
            holder.tvTenMonAn.setText(monAn.getTenMonAn());
            holder.tvGiaTien.setText(monAn.getGiaTien()+"");
            holder.tvDonViTinh.setText(monAn.getDonViTinh());
            // Load image theo duong dan file luu trong db
            if (monAn.getHinhAnh() != null && !monAn.getHinhAnh().isEmpty()) {
                File imageFile = new File(monAn.getHinhAnh());
                if (imageFile.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    holder.imgMonAn.setImageBitmap(bitmap);
                }
            }
            holder.ibtnChinhSua.setOnClickListener(v -> {
                itemMonAnListener.onClickUpdateButton(monAn);
            });
            //holder.imgMonAn.setImageDrawable(ContextCompat.getDrawable(holder.imgMonAn.getContext(), monAn.getHinhAnh()));
        }
    }

    @Override
    public int getItemCount() {
        return dsMonAn.size();
    }
    class MonAnHolder extends RecyclerView.ViewHolder {
        ImageView imgMonAn;
        TextView tvTenMonAn, tvGiaTien, tvDonViTinh;
        ImageButton ibtnChinhSua;
        ConstraintLayout layoutitem;//layout chua item
        public MonAnHolder(@NonNull View itemView) {
            super(itemView);
            //Anh xa
            imgMonAn = itemView.findViewById(R.id.imgMonAn);
            tvTenMonAn = itemView.findViewById(R.id.tvTenMonAn);
            tvGiaTien = itemView.findViewById(R.id.tvGia);
            tvDonViTinh = itemView.findViewById(R.id.tvDonViTinh);
            ibtnChinhSua = itemView.findViewById(R.id.ibtnChinhSua);
            layoutitem = itemView.findViewById(R.id.layout_item_mon_an);//layout chua item
        }
    }
}



