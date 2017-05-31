package com.bearcave.passageplanning.base;


import android.content.Intent;
import android.os.Parcelable;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey;

import butterknife.OnClick;

/**
 * BasePoorManager with FloatingActionButton, which opens DAO Editor.
 *
 * @see BasePoorManagerFragment
 * @author Michał Wąsowicz
 * @version 1.0
 */
public abstract class BaseManagerFragment<DAO extends Parcelable & DatabaseElementWithCustomKey<T>, T>
        extends BasePoorManagerFragment<DAO, T> {


    @OnClick(R.id.open_editor)
    public void openEditor() {
        openEditor(null);
    }

    /**
     * @param element - Data Access Object. If null, then editor creates a new one.
     */
    public void openEditor(DAO element){
        Intent intent = new Intent(getContext(), getEditorClass());

        if(element != null){
            intent.putExtra(BaseEditorActivity.Companion.getEDITOR_RESULT(), element);
        }

        putExtra(intent);
        startActivityForResult(intent, BaseEditorActivity.Companion.getEDITOR_REQUEST());
    }

    protected abstract Class<? extends BaseEditorActivity<DAO>> getEditorClass();

    /**
     * @param mail - used to send object, excluding DAO.
     */
    protected void putExtra(Intent mail){
        // for possible override
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_base_manager;
    }

    /**
     * @param requestCode
     * @param resultCode - to check if element is updated or created.
     * @param data - DAO is sent in this intent.
     *
     * @see BaseManagerFragment#openEditor(Parcelable)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BaseEditorActivity.Companion.getEDITOR_REQUEST()) {
            if (resultCode == BaseEditorActivity.Companion.getEDITOR_CREATED()) {
                DAO result = data.getParcelableExtra(BaseEditorActivity.Companion.getEDITOR_RESULT());
                onDataCreated(result);

            } else if (resultCode == BaseEditorActivity.Companion.getEDITOR_UPDATED()) {
                DAO result = data.getParcelableExtra(BaseEditorActivity.Companion.getEDITOR_RESULT());
                onDataUpdated(result);
            }
        }
    }

    protected abstract void onDataCreated(DAO result);

    protected abstract void onDataUpdated(DAO result);
}
