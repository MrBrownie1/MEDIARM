package com.example.mediarm.ImgStorage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.R;
import com.squareup.picasso.Picasso;

public class ViewHolderConceptos extends RecyclerView.ViewHolder {

    View mView;
    private  ViewHolderConceptos.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void OnItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderConceptos.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public ViewHolderConceptos(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.OnItemLongClick(view,getAdapterPosition());
                return true;
            }
        });

    }

    public void StorageConcepto(Context context, String img, String nombre, String concepto){
        ImageView ImagenConcepto;
        TextView NombreConceptoI;
        TextView DescripcionConceptoI;

        ImagenConcepto = mView.findViewById(R.id.ImagenConcepto);
        NombreConceptoI = mView.findViewById(R.id.NombreConceptoI);
        DescripcionConceptoI = mView.findViewById(R.id.DescripcionConceptoI);

        NombreConceptoI.setText(nombre);
        DescripcionConceptoI.setText(concepto);

        try{
            Picasso.get().load(img).into(ImagenConcepto);
        }
        catch (Exception e){
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
