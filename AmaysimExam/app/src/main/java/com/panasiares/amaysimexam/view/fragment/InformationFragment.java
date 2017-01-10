package com.panasiares.amaysimexam.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.panasiares.amaysimexam.R;
import com.panasiares.amaysimexam.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InformationFragment extends Fragment {

    private HashMap<String, String> mHashMap;

    public static InformationFragment newInstance(HashMap<String, String> data) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putSerializable("info_data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHashMap = new HashMap<>();
        Bundle bundle = getArguments();
        if(bundle.getSerializable("info_data") != null) {
            mHashMap = (HashMap<String, String>) bundle.getSerializable("info_data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        SubscriptionAdapter adapter = new SubscriptionAdapter(mHashMap);
        listView.setAdapter(adapter);

        return view;
    }

    private class SubscriptionAdapter extends BaseAdapter {

        private final ArrayList mData;

        public SubscriptionAdapter(Map<String, String> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
            return (Map.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Map.Entry<String, String> item = getItem(position);
            if (viewHolder.mTxtItemKey != null && viewHolder.mTxtItemValue != null) {
                String key = item.getKey().replace("-", " ");
                String value;

                if (key.contains("balance")) {
                    value = Utils.megaToGigaBytes(item.getValue());
                } else if (key.contains("price")) {
                    value = Utils.centToDollar(item.getValue());
                } else {
                    value = getItemValue(item.getValue());
                }

                viewHolder.mTxtItemKey.setText(key);
                viewHolder.mTxtItemValue.setText(value);
            }

            return convertView;
        }

        private String getItemValue(String value) {
            if (value.equals("true")) {
                return "yes";
            } else if (value.equals("false")) {
                return "no";
            } else if (value.equals("null")) {
                return "not specified";
            }
            return value;
        }
    }

    public static class ViewHolder {
        TextView mTxtItemKey;
        TextView mTxtItemValue;

        private ViewHolder(View itemView) {
            super();
            setupViews(itemView);
        }

        private void setupViews(View view) {
            mTxtItemKey = (TextView) view.findViewById(R.id.txt_item_key);
            mTxtItemValue = (TextView) view.findViewById(R.id.txt_item_value);
        }
    }
}
