package identify;

import lombok.Data;
import lombok.NonNull;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.*;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfo implements Serializable {
    @NonNull private final String cn, uid, mail;
    private final String mobile, department, title, l;
    @NonNull private final String[] groups;

    public String toVCard() {
        List<Property> properties = new ArrayList<Property>();
        properties.add(Kind.INDIVIDUAL);
        properties.add(new Name(cn));
        properties.add(new Email(mail));
        if (mobile != null)
            properties.add(new Telephone(mobile));
        if (title != null)
            properties.add(new Title(title));
        if (department != null)
            properties.add(new Org(department));
        if (l != null)
            properties.add(new Address(null, null, null, l.trim(), null, null, null, Type.WORK));
        try {
            properties.add(new Url(new URI(String.format("https://wiki.spotify.net/wiki/User:%s", uid))));
        } catch (URISyntaxException ignored) {}

        return new VCard(properties).toString();
    }
}