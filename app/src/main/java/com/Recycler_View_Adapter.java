package com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.Adapter_Holder> {

    Context context;

    ArrayList<DTO1> arrayList = new ArrayList<>();
    private ItemClickListener clickListener;

    public Recycler_View_Adapter(Context context, ArrayList<DTO1> arrayList){

        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public Adapter_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);

        return new Adapter_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Holder holder, int position) {
    holder.quiz.setText(String.valueOf(arrayList.get(position).getQuestion()));
    holder.option1.setText(arrayList.get(position).getOpt1());
    holder.option2.setText(arrayList.get(position).getOpt2());
    holder.option3.setText(arrayList.get(position).getOpt3());
    holder.option4.setText(arrayList.get(position).getOpt4());
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class Adapter_Holder extends RecyclerView.ViewHolder {
        TextView quiz;
        Button option1, option2, option3, option4;
        public Adapter_Holder(@NonNull View itemView) {
            super(itemView);
            quiz = (TextView) itemView.findViewById(R.id.quiz);
            option1 = (Button) itemView.findViewById(R.id.option1);
            option2 = (Button) itemView.findViewById(R.id.option2);
            option3 = (Button) itemView.findViewById(R.id.option3);
            option4 = (Button) itemView.findViewById(R.id.option4);

        }
    }
}
