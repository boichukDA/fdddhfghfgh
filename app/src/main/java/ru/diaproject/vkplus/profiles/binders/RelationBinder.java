package ru.diaproject.vkplus.profiles.binders;


import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.StringUtils;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;
import ru.diaproject.vkplus.profiles.model.items.RelationItem;
import ru.diaproject.vkplus.profiles.viewholders.KeyValueViewHolder;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import rx.Observable;
import rx.functions.Action1;

public class RelationBinder extends ItemDataBinder<KeyValueViewHolder> {

    public RelationBinder(VKProfileDetailsActivity context, UserDataContainer container, ColorScheme colorScheme) {
        super(context, container, colorScheme);
    }

    @Override
    public KeyValueViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_main_key_value_item, parent, false);
        KeyValueViewHolder holder = new KeyValueViewHolder(v);
        holder.itemView.setBackgroundColor(getColorScheme().getCardColor());
        holder.valueText.setTextColor(getColorScheme().getTextColor());
        holder.keyText.setTextColor(getColorScheme().getTextColor());
        return holder;
    }

    @Override
    public void bindViewHolder(final KeyValueViewHolder holder, int position) {
        final RelationItem item = (RelationItem) getContainer().get(position).getItem();

        holder.keyText.setText(item.getKey());
        holder.valueText.setMovementMethod(LinkMovementMethod.getInstance());

        final Spannable spannable = item.getSpanValue();
        if (spannable == null || "".equals(spannable.toString())) {
            final StringBuilder newSpanString = new StringBuilder(item.getRelationName());

            if (item.isPartnerContains()){
                newSpanString.append(" ");
                newSpanString.append(item.getPretext());
                newSpanString.append(" ");

                if (StringUtils.isNullOrEmpty(item.getCaseFirstName())
                && StringUtils.isNullOrEmpty(item.getCaseLastName())){
                    VKMainExecutor.executeRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Observable.from(VKMainExecutor.request(createCaseQuery(item)))
                                    .subscribe(new Action1<IDataUser>() {
                                        @Override
                                        public void call(IDataUser userExt) {
                                            item.setCaseFirstName(userExt.getFirstName());
                                            item.setCaseLastName(userExt.getLastName());

                                            Spannable spanName = createSpannableString(item, newSpanString);

                                            item.setSpanValue(spanName);
                                            holder.valueText.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.valueText.setText(item.getSpanValue());
                                                }
                                            });
                                        }
                                    });
                        }
                    });
                }
            }else {
                item.setSpanValue(VkStringUtils.toSpannable(newSpanString.toString()));
                holder.valueText.setText(item.getSpanValue());

            }
        } else holder.valueText.setText(spannable);
    }

    private VKQuery<IDataUser> createCaseQuery(RelationItem item){
        VKQuery<IDataUser> query = VKApi.users(getContext().getUser())
                .getById(item.getPartnerId())
                .and(VKParameter.NAME_CASE, item.getCaseTypeForQuery())
                .build();

        return query;
    }

    private Spannable createSpannableString(RelationItem item, StringBuilder newSpanString){

        SpannableString spannableString = new SpannableString(newSpanString + item.getCaseFirstName() + " " + item.getCaseLastName());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                System.out.print(textView);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getColorScheme().getTextColor());
            }
        };
        spannableString.setSpan(clickableSpan,newSpanString.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
