package appapae.apae.app.apae.adapters.partner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appapae.apae.app.apae.R;
import appapae.apae.app.apae.adapters.ItemClickListener;

public class RecyclerPartnerViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView titlePartner;
    private final TextView discount;
    private final ImageView photo;

    private ItemClickListener itemClickListener;

    public RecyclerPartnerViewHolder(View itemView, TextView tittle, TextView discount, ImageView img) {
        super(itemView);

        this.titlePartner = tittle;
        this.discount = discount;
        this.photo = img;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }

        public static RecyclerPartnerViewHolder newInstance(View parent) {

            TextView tvTitle = parent.findViewById(R.id.partnerTittle);
            TextView tvDiscount = parent.findViewById(R.id.partnerDiscount);
            ImageView imgView =  parent.findViewById(R.id.partnerPhoto);

            return new RecyclerPartnerViewHolder(parent,tvTitle,tvDiscount,imgView);
        }

    public void setText(CharSequence text) {
        titlePartner.setText(text);
    }
    public void setDiscount(CharSequence text) {
        discount.setText(text);
    }
    public ImageView getImgView() {
        return photo;
    }
}

