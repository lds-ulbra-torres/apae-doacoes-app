package laz.appapae.model;

import java.util.ArrayList;

/**
 * Created by laz on 21/06/17.
 */

public interface PartnerCallback<S>{

    void onSuccess(ArrayList<Partner> partners);

    void onError();
}
