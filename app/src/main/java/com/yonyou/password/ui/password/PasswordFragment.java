package com.yonyou.password.ui.password;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yonyou.password.R;
import com.yonyou.password.provider.Password;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PasswordFragment extends Fragment {
    public static final int REQUEST_CODE_ADD = 1;
    private OnListFragmentInteractionListener mListener;
    private MyPasswordRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PasswordFragment() {
    }

    public static PasswordFragment newInstance(int columnCount) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            PasswordList.ITEMS.addAll(getPasswordList());
            adapter = new MyPasswordRecyclerViewAdapter(PasswordList.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD:
                adapter.bind(getPasswordList());
                break;
        }
    }

    private ArrayList<PasswordList.Password> getPasswordList() {
        Cursor cursor = getContext().getContentResolver().query(Password.PasswordTable.CONTENT_URI, null,
                null, null, null);
        ArrayList<PasswordList.Password> passwordArrayList = new ArrayList<PasswordList.Password>();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(Password.PasswordTable._ID));
                    String name = cursor.getString(cursor.getColumnIndex(Password.PasswordTable.NAME));
                    String password = cursor.getString(cursor.getColumnIndex(Password.PasswordTable.PASSWORD));
                    PasswordList.Password passwordItem = new PasswordList.Password(id, name, password);
                    passwordArrayList.add(passwordItem);
                }
            } finally {
                cursor.close();
            }
        }
        return passwordArrayList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PasswordList.Password item);
    }
}
