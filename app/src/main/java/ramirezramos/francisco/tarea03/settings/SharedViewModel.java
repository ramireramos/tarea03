package ramirezramos.francisco.tarea03.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isDeleteEnabled = new MutableLiveData<>(true);

    public LiveData<Boolean> getIsDeleteEnabled() {
        return isDeleteEnabled;
    }

    public void setIsDeleteEnabled(boolean isEnabled) {
        isDeleteEnabled.setValue(isEnabled);
    }
}
