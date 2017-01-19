package com.yonyou.password.ui.password;

import android.content.ContentUris;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yonyou.password.R;
import com.yonyou.password.provider.Password;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.yonyou.password.ui.password.PasswordList.Password} and makes a call to the
 * specified {@link PasswordFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPasswordRecyclerViewAdapter extends RecyclerView.Adapter<MyPasswordRecyclerViewAdapter.ViewHolder> {

    private final List<PasswordList.Password> mValues;
    private final PasswordFragment.OnListFragmentInteractionListener mListener;

    public MyPasswordRecyclerViewAdapter(List<PasswordList.Password> items, PasswordFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void bind(List<PasswordList.Password> items) {
        if (mValues != null) {
            mValues.clear();
            mValues.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_password, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(" ");
        holder.mContentView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getContentResolver().delete(ContentUris.withAppendedId(Password.PasswordTable.CONTENT_URI, Long.parseLong(holder.mItem.id)), null, null);
                ArrayList<PasswordList.Password> passwordArrayList = PasswordFragment.getPasswordList(v.getContext());
                bind(passwordArrayList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button mDelButton;
        public PasswordList.Password mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDelButton = (Button) view.findViewById(R.id.del);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
