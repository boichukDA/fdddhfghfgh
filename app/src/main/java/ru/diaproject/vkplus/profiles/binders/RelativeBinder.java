package ru.diaproject.vkplus.profiles.binders;

import android.content.Intent;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.core.utils.StringUtils;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.model.users.DataUserList;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.model.users.extusers.relatives.Relative;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;
import ru.diaproject.vkplus.profiles.model.items.RelativeItem;
import ru.diaproject.vkplus.profiles.viewholders.KeyValueViewHolder;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import rx.Observable;
import rx.functions.Action1;

public class RelativeBinder extends RelationBinder  {
    public RelativeBinder(VKProfileDetailsActivity context, UserDataContainer container, ColorScheme colorScheme) {
        super(context, container, colorScheme);
    }

    @Override
    public void bindViewHolder(final KeyValueViewHolder holder, int position) {
        final RelativeItem item = (RelativeItem) getContainer().get(position).getItem();

        holder.keyText.setText(item.getSpanKey());
        holder.valueText.setMovementMethod(LinkMovementMethod.getInstance());

        final List<Relative> items = item.getItems();
        final List<Relative> simpleItems = new ArrayList<>();
        final List<Relative> userItems = new ArrayList<>();

        for (Relative currentItem:items){
            if (currentItem.getId() < 0)
                simpleItems.add(currentItem);
             else userItems.add(currentItem);
        }

        final StringBuilder result = new StringBuilder();

        for (Relative currentItem:simpleItems) {
            result.append(currentItem.getName());
            result.append("\n");
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);

        if (!userItems.isEmpty()) {
            if (StringUtils.isNullOrEmpty(userItems.get(0).getName())) {
                VKMainExecutor.executeRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Observable.from(VKMainExecutor.request(createCaseQuery(userItems)))
                                .subscribe(new Action1<DataUserList>() {

                                    @Override
                                    public void call(DataUserList dataUserList) {
                                        for (DataUserExt user : dataUserList.getItems()) {
                                            for (Relative currentItem : userItems)
                                                if (user.getId().equals(currentItem.getId())) {
                                                    currentItem.setName(user.getFirstName() + " " + user.getLastName());
                                                    currentItem.setUser(user);
                                                }
                                        }
                                        Spannable spannable = createSpannableString(result, userItems);
                                        item.setSpanValue(spannable);
                                        getContext().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                holder.valueText.setText(item.getSpanValue());
                                            }
                                        });
                                    }
                                });
                    }
                });
            } else {
                Spannable spannable = createSpannableString(result, userItems);
                item.setSpanValue(spannable);
                holder.valueText.setText(item.getSpanValue());
            }
        }else {
            Spannable spannable = VkStringUtils.toSpannable(result.toString());
            item.setSpanValue(spannable);
            holder.valueText.setText(item.getSpanValue());
        }
    }

    private VKQuery<DataUserList> createCaseQuery(List<Relative> items) {
        List<Integer> ids = new ArrayList<>();
        for (Relative item:items)
            ids.add(item.getId());

        return VKApi.users(getContext().getUser()).getAll(ids).with(VKParameter.FIELDS, "sex").build();
    }

    private Spannable createSpannableString(StringBuilder previousResult, List<Relative> userItems){
        if (previousResult.length() > 0)
            previousResult.append("\n");

        for (Relative currentItem:userItems){
            currentItem.setStartSpan(previousResult.length());
            previousResult.append(currentItem.getName());
            currentItem.setEndSpan(previousResult.length());
            previousResult.append("\n");
        }
        previousResult.deleteCharAt(previousResult.length() - 1);

        Spannable result = VkStringUtils.toSpannable(previousResult.toString());

        for (final Relative currentItem:userItems){
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    IDataUser user = currentItem.getUser();
                    if (user!= null) {
                        Intent intent = new Intent(getContext(), VKProfileDetailsActivity.class);
                        intent.putExtra(DataConstants.USER, user);
                        getContext().startActivity(intent);
                        getContext().overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                    }
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(getColorScheme().getTextColor());
                }
            };
            result.setSpan(clickableSpan, currentItem.getStartSpan(), currentItem.getEndSpan(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return result;
    }
}
