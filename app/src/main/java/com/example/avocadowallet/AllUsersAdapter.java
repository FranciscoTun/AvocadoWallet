package com.example.avocadowallet;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.avocadowallet.Transacciones.User;
import java.util.ArrayList;

public class AllUsersAdapter extends RecyclerView.Adapter {
    private Context mContext;
    ArrayList<User> userList;

    public AllUsersAdapter(Context mContext, ArrayList<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_user_view, null);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }


    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        UserViewHolder holder = (UserViewHolder) viewHolder;
        holder.ivProfile.setImageResource(userList.get(i).imageResourceId);
        holder.tvName.setText(userList.get(i).userName);
        holder.tvMobile.setText(userList.get(i).userMobile);
        holder.tvEmail.setText(userList.get(i).userEmail);
        holder.tvCreatedDate.setText(userList.get(i).userCreatedDate);
    }


    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfile;
        private TextView tvName;
        private TextView tvMobile;
        private TextView tvEmail;
        private TextView tvCreatedDate;

        public UserViewHolder(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvCreatedDate = (TextView) itemView.findViewById(R.id.tvCreatedDate);
        }
    }
}
