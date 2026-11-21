package ua.lpnu.kzp;

/**
 * Визначає конкретний тип посилки
 * за допомогою pattern matching switch.
 */
public final class ParcelTypeResolver {

    private ParcelTypeResolver() {
    }

    /**
     * Повертає текстовий опис типу посилки.
     *
     * @param parcel посилка
     * @return опис типу доставлення
     */
public static String describe(Parcel parcel) {
    return switch (parcel) {
        case RegularParcel regular ->
                "Звичайне доставлення";

        case ExpressParcel express ->
                "Експрес-доставлення";
    };
}
}