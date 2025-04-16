package pl.pollub.android.myapplication.phonesExercise.view;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ValueIterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pollub.android.myapplication.R;
import pl.pollub.android.myapplication.phonesExercise.data.PhoneEntity;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder>{
    private LayoutInflater layoutInflater;
    private List<PhoneEntity> phoneList;
    private Context context;
    private OnClickListener listener;

    public interface OnClickListener {
        void onItemClick(PhoneEntity phone);
    }

    public PhoneListAdapter(Context context, OnClickListener listener){
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
        phoneList = null;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhoneListAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.phone_row, parent, false);
        return new PhoneViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneListAdapter.PhoneViewHolder holder, int position) {
        holder.bindToPhoneViewHolder(position);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                listener.onItemClick(phoneList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (phoneList != null){
            return phoneList.size();
        }
        return 0;
    }

    public PhoneEntity getItemAt(int position){
        return phoneList.get(position);
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder{
        TextView phoneManufacturerTextView;
        TextView phoneModelTextView;
        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneManufacturerTextView = itemView.findViewById(R.id.phone_manufacturer);
            phoneModelTextView = itemView.findViewById(R.id.phone_model);
        }

        public void bindToPhoneViewHolder(int position){
            phoneManufacturerTextView.setText(phoneList.get(position).getManufacturer());
            phoneModelTextView.setText(phoneList.get(position).getModel());
        }
    }

    public void setPhoneList(List<PhoneEntity> phones){
        this.phoneList = phones;
        notifyDataSetChanged();
    }
}
