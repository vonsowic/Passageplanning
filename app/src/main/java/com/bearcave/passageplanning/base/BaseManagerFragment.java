package com.bearcave.passageplanning.base;


import android.content.Intent;
import android.os.Parcelable;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.DatabaseElementWithCustomKey;

import butterknife.OnClick;

/**
 * BasePoorManager with FloatingActionButton
 */
public abstract class BaseManagerFragment<DAO extends Parcelable & DatabaseElementWithCustomKey<T>, T>
        extends BasePoorManagerFragment<DAO, T> {


    @OnClick(R.id.open_editor)
    public void openEditor() {
        openEditor(null);
    }

    public void openEditor(DAO element){
        Intent intent = new Intent(getContext(), getEditorClass());

        if(element != null){
            intent.putExtra(BaseEditorActivity.EDITOR_RESULT, element);
        }

        putExtra(intent);

        startActivityForResult(intent, BaseEditorActivity.EDITOR_REQUEST);
    }

    protected abstract Class<?> getEditorClass();

    protected void putExtra(Intent mail){
        // for possible override
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_base_manager;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BaseEditorActivity.EDITOR_REQUEST) {
            if (resultCode == BaseEditorActivity.EDITOR_CREATED) {
                DAO result = data.getParcelableExtra(BaseEditorActivity.EDITOR_RESULT);

                insert(result);
                getAdapter().add(
                        result
                );

            } else if (resultCode == BaseEditorActivity.EDITOR_UPDATED) {
                DAO result = data.getParcelableExtra(BaseEditorActivity.EDITOR_RESULT);

                update(result);
                getAdapter().update(result);
            }
        }
    }
}
