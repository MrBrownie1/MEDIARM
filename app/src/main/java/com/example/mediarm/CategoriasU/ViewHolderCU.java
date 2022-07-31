package com.example.mediarm.CategoriasU;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.R;
import com.squareup.picasso.Picasso;

public class ViewHolderCU extends RecyclerView.ViewHolder {

    View mView;
    private  ViewHolderCU.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderCU.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public ViewHolderCU(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }

    public void StorageCU(Context context, String categoria, String img){
        ImageView ImgCategoria;
        TextView NombreCategoria;

        ImgCategoria= mView.findViewById(R.id.ImgCategoria);
        NombreCategoria = mView.findViewById(R.id.NombreCategoria);

        NombreCategoria.setText(categoria);


        try{
            Picasso.get().load(img).placeholder(R.drawable.icn_galeria).into(ImgCategoria);
        }
        catch (Exception e){
            //Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            Picasso.get().load(R.drawable.icn_galeria).into(ImgCategoria);
        }
    }
}
