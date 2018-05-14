package com.example.rodrigo.ligue4.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rodrigo.ligue4.R;
import com.example.rodrigo.ligue4.ViewHolders.StateViewHolder;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 19/09/2016.
 */
public class StateAdapter extends BaseAdapter {

    private static final String TESTE = "TESTE";

    private Context context;
    private ArrayList<Integer> pecasTabuleiro;

    public StateAdapter(Context context, ArrayList<Integer> pecasTabuleiro) {
        this.context = context;
        this.pecasTabuleiro = pecasTabuleiro;
    }

    @Override
    public int getCount() {
        return pecasTabuleiro.size();
    }

    @Override
    public Object getItem(int position) {
        return pecasTabuleiro.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  // Método chamado para cada item da lista

        View view;
        StateViewHolder holder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.state, parent, false);
            holder = new StateViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (StateViewHolder) view.getTag();
        }

        Integer peca = (Integer) getItem(position);
        if (peca == 1) {
            holder.image.setImageResource(R.drawable.ic_coin_yellow);
        } else if(peca == 2){
            holder.image.setImageResource(R.drawable.ic_coin_red);
        }

        return view;

    }

}
