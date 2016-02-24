package ru.diaproject.vkplus.profiles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.model.users.OwnerSex;
import ru.diaproject.vkplus.model.users.extusers.City;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.model.users.extusers.occupations.Occupation;
import ru.diaproject.vkplus.model.users.extusers.occupations.OccupationType;
import ru.diaproject.vkplus.model.users.extusers.relations.RelationPartner;
import ru.diaproject.vkplus.model.users.extusers.relatives.Relative;
import ru.diaproject.vkplus.model.users.extusers.relatives.RelativiesType;
import ru.diaproject.vkplus.profiles.model.items.CaseType;
import ru.diaproject.vkplus.profiles.model.items.InfoItem;
import ru.diaproject.vkplus.profiles.model.items.KeyValueItem;
import ru.diaproject.vkplus.profiles.model.items.RelationItem;
import ru.diaproject.vkplus.profiles.model.items.RelativeItem;

/**
 * *Enum ProfileItemType determines view holder type
 * Item for binders
 */
public class ShortInfoContainer {
    private List<InfoItem> items;
    public ShortInfoContainer(DataUserExt dataUserExt, Context context) {
        items = new ArrayList<>();

        String bDate = dataUserExt.getBdate();
        if (bDate!= null && !"".equals(bDate))
            items.add(createBDateItem(bDate, context));

        City city = dataUserExt.getCity();
        if (city != null)
            items.add(createCityItem(city, context));

        Occupation occupation = dataUserExt.getOccupation();
        if (occupation!= null)
            items.add(createOccupationItem(occupation, dataUserExt, context));

        Integer relation = dataUserExt.getRelation();

        if (relation!= 0)
            items.add(createRelation(relation, dataUserExt, dataUserExt.getSex(), context));

        List<Relative> relativiesItems = dataUserExt.getRelativies();
        if (!(relativiesItems == null || relativiesItems.isEmpty())){
            for (RelativiesType type: RelativiesType.values()) {
                List<Relative> grandparents = getRelativiesItemsByType(relativiesItems, type);
                if (!grandparents.isEmpty())
                    items.add(createRelatvityItem(grandparents,type, context));
            }
        }
    }

    private InfoItem createRelatvityItem(List<Relative> grandparents, RelativiesType grandparent, Context context) {
        InfoItem<RelativeItem> infoItem = new InfoItem<>(ProfileItemType.RELATIVE);
        RelativeItem item = new RelativeItem();

        String key = context.getResources().getStringArray(R.array.relative_names_array)[grandparent.ordinal()];
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setItems(grandparents);
        infoItem.setItem(item);
        return infoItem;
    }

    private List<Relative> getRelativiesItemsByType(List<Relative> relativiesItems, RelativiesType type) {
        List<Relative> result = new ArrayList<>();

        for (Relative item:relativiesItems)
        if (item.getType().equals(type))
            result.add(item);
        return result;
    }

    private InfoItem createRelation(Integer relation, DataUserExt user, OwnerSex sex, Context context) {
        String key = context.getString(R.string.profile_relation);
        String relationName = "";
        CaseType caseType = CaseType.NOM;
        String pretext = "";
        String caseTypeForQuery = CaseType.NOM.toString();
        Integer partnerId = 0;
        boolean partnerContains = false;
        String nomFirstName;
        String nomLastName;

        switch (relation){
            case 1:
                relationName = context.getResources().getStringArray(R.array.relation_not_maried)[sex.ordinal()];
                break;
            case 2:
                relationName = context.getResources().getStringArray(R.array.relation_has_friend)[sex.ordinal()];
                break;
            case 3:
                relationName = context.getResources().getStringArray(R.array.relation_engaged)[sex.ordinal()];
                caseType = CaseType.INS;
                pretext = context.getString(R.string.profile_relation_with);
                caseTypeForQuery = context.getString(R.string.case_type_ins);
                break;
            case 4:
                relationName = context.getResources().getStringArray(R.array.relation_married)[sex.ordinal()];
                caseType = user.getSex().equals(OwnerSex.MAN)?CaseType.DAT:CaseType.INS;
                pretext = context.getResources().getStringArray(R.array.profile_relation_married_on)[sex.ordinal()];
                caseTypeForQuery = context.getResources().getStringArray(R.array.case_type_dat_arr)[sex.ordinal()];
                break;
            case 5:
                relationName = context.getResources().getStringArray(R.array.relation_complicated)[sex.ordinal()];
                caseType = CaseType.INS;
                pretext = context.getString(R.string.profile_relation_in);
                caseTypeForQuery = context.getString(R.string.case_type_dat);
                break;
            case 6:
                relationName = context.getResources().getStringArray(R.array.relation_act_looking)[sex.ordinal()];
                break;
            case 7:
                relationName = context.getResources().getStringArray(R.array.relation_love)[sex.ordinal()];
                caseType = CaseType.ACC;
                pretext = context.getString(R.string.profile_relation_in);
                caseTypeForQuery = context.getString(R.string.case_type_acc);
                break;
        }
        RelationItem item = new RelationItem();
        item.setRelationName(relationName);
        item.setKey(key);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setCaseType(caseType);
        item.setPretext(pretext);
        item.setCaseTypeForQuery(caseTypeForQuery);
        item.setPartnerContains(partnerContains);

        RelationPartner partner = user.getRelationPartner();
        if (partner!= null) {
            partnerContains = true;
            partnerId = partner.getId();
            nomFirstName = partner.getFirstName();
            nomLastName = partner.getFirstName();

            item.setPartnerContains(partnerContains);
            item.setPartnerId(partnerId);
            item.setNomFirstName(nomFirstName);
            item.setNomLastName(nomLastName);
        }

        InfoItem<RelationItem> infoItem = new InfoItem<>(ProfileItemType.RELATION);
        infoItem.setItem(item);
        return infoItem;
    }

    private InfoItem createOccupationItem(Occupation occupation, DataUserExt dataUserExt, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key;
        String value;
        if (occupation.getType().equals(OccupationType.SCHOOL)
                || occupation.getType().equals(OccupationType.UNIVERSITY))
            key = context.getString(R.string.profile_school);
        else
            key = context.getString(R.string.profile_work);

        value = occupation.getName();
        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }

    private InfoItem createBDateItem(String bDate, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key = context.getString(R.string.profile_bdate);
        String value = DateUtils.parseBDateString(bDate);

        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }

    private InfoItem createCityItem(City city, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key = context.getString(R.string.profile_city);
        String value = city.getTitle();

        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }

    public int getItemCount() {
        return items.size();
    }

    public InfoItem get(int position) {
        return items.get(position);
    }
}
