package ru.romazanov.testapp1.ui.second;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.romazanov.testapp1.R;
import ru.romazanov.testapp1.db.AuthEntity;

public class AuthDataAdapter extends RecyclerView.Adapter<AuthDataAdapter.ViewHolder> {

    private List<AuthEntity> states;

    void setStates(List<AuthEntity> list) {
        states = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuthDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthDataAdapter.ViewHolder holder, int position) {
        holder.text2.setText(states.get(position).getCode());
        holder.text1.setText(states.get(position).date);
    }

    @Override
    public int getItemCount() {
        if (states != null) return states.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text1;
        TextView text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
        }
    }
}
