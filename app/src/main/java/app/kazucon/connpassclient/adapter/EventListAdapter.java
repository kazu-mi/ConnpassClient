package app.kazucon.connpassclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.activity.EventActivity;
import app.kazucon.connpassclient.databinding.RowEventBinding;
import app.kazucon.connpassclient.model.EventResponse;
import app.kazucon.connpassclient.viewmodel.EventRowViewModel;

/**
 * Created by kazuumi on 17/03/18.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.SelfViewHolder> {

    private Activity activity;
    private ArrayList<EventResponse.Event> events = new ArrayList<>();

    public EventListAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addEvents(List<EventResponse.Event> newEvents) {
        this.events.addAll(newEvents);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.events.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public SelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_event,
                parent,
                false
        );

        return new SelfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelfViewHolder holder, final int position) {
        holder.rowEventBind.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventActivity.start(activity, events.get(position));
            }
        });
        holder.rowEventBind.setVariable(BR.eventRowViewModel, new EventRowViewModel(events.get(position)));
        holder.rowEventBind.executePendingBindings();
    }

    class SelfViewHolder extends RecyclerView.ViewHolder {

        final RowEventBinding rowEventBind;

        SelfViewHolder(View view) {
            super(view);

            rowEventBind = DataBindingUtil.bind(view);
        }
    }
}
