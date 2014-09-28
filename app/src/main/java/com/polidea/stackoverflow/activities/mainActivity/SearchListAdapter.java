package com.polidea.stackoverflow.activities.mainActivity;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.polidea.stackoverflow.R;
import com.polidea.stackoverflow.api.stackoverflow.models.SearchResultModel;

import java.util.ArrayList;

/**
 * Created by Hubert on 05.09.2014.
 */
public class SearchListAdapter extends ArrayAdapter<SearchResultModel> {

    private ArrayList<SearchResultModel> values;
    private DisplayImageOptions options;
    private LayoutInflater inflater;


    public SearchListAdapter(Context context, ArrayList<SearchResultModel> values, DisplayImageOptions options) {
        super(context, R.layout.list_row_search, values);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = values;
        this.options = options;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder = null;
        if(rowView == null){
            rowView = inflater.inflate(R.layout.list_row_search, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ownerImage       = (ImageView) rowView.findViewById(R.id.ownerImage);
            viewHolder.ownerName        = (TextView) rowView.findViewById(R.id.ownerName);
            viewHolder.questionAnswers  = (TextView) rowView.findViewById(R.id.questionAnswers);
            viewHolder.questionTitle    = (TextView) rowView.findViewById(R.id.questionTitle);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        SearchResultModel searchResultModel = values.get(position);

        ImageLoader.getInstance().displayImage(searchResultModel.getOwnerModel().getImageURL(),viewHolder.ownerImage, options);
        viewHolder.ownerName.setText(searchResultModel.getOwnerModel().getUserName());
        viewHolder.questionAnswers.setText("Answers: "+searchResultModel.getAnswerCount());
        viewHolder.questionTitle.setText(Html.fromHtml(searchResultModel.getTitle()));

        return rowView;
    }

    private class ViewHolder{
        private ImageView ownerImage;
        private TextView ownerName;
        private TextView questionAnswers;
        private TextView questionTitle;
    }
}
