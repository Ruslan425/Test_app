package ru.romazanov.testapp1.ui.second;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.romazanov.testapp1.R;
import ru.romazanov.testapp1.db.AuthEntity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<AuthEntity> states;

    void setStates(List<AuthEntity> list) {
        states = list;
        notifyDataSetChanged();
    }


    MyAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        TextView text1 = holder.itemView.findViewById(R.id.text1);
        TextView text2 = holder.itemView.findViewById(R.id.text2);
        text2.setText(states.get(position).getCode());
        text1.setText(states.get(position).date);
    }

    @Override
    public int getItemCount() {
        if (states != null)return states.size();
        return 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
