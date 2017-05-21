package com.bearcave.passageplanning.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.base.CRUD;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by miwas on 20.05.17.
 */

public abstract class BaseManagerAdapter<T extends OnNameRequestedListener> extends BaseExpandableListAdapter {

    private ArrayList<T> container;
    private Context context;
    private LayoutInflater inflater;
    private final SparseArray<OnMenuItemSelectedAction> menuActions = new SparseArray<>();
    private final CRUD<T> database;


    public BaseManagerAdapter(BaseManagerFragment parent, Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        database = (CRUD<T>) parent;

        menuActions.put(EDIT_CODE, t -> database.update((T) t));
        menuActions.put(DELETE_CODE, w -> {
            database.delete((T) w);
            container.remove(w);
            notifyDataSetChanged();
        });

        container = (ArrayList<T>) database.readAll();
    }

    public void add(T element){
        container.add(element);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return container.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return container.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = getInflater().inflate(R.layout.manager_group_item, parent, false);

        TextView title = ButterKnife.findById(view, R.id.name);
        title.setText(container.get(groupPosition).getName());

        ImageView option = ButterKnife.findById(view, R.id.options_button);
        option.setOnClickListener(v -> showPopupMenu(v, container.get(groupPosition)));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void showPopupMenu(View anchor, T selected){
        PopupMenu menu = new PopupMenu(context, anchor);
        menu.getMenu().add(Menu.NONE, EDIT_CODE, Menu.NONE, context.getString(R.string.action_edit));
        menu.getMenu().add(Menu.NONE, DELETE_CODE, Menu.NONE, context.getString(R.string.action_delete));

        menu.setOnMenuItemClickListener(item -> {
            menuActions.get(item.getItemId()).run(selected);

            return true;
        });

        menu.show();
    }

    protected LayoutInflater getInflater() {
        return inflater;
    }

    protected ArrayList<T> getContainer() {
        return container;
    }

    protected CRUD<T> getDatabase() {
        return database;
    }

    protected static final int EDIT_CODE = 1;
    protected static final int DELETE_CODE = 2;

    @FunctionalInterface
    protected interface OnMenuItemSelectedAction<T>{

        void run(T element);
    }


}
