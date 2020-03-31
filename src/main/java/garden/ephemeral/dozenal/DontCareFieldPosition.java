package garden.ephemeral.dozenal;

import java.text.FieldPosition;
import java.text.Format;

/**
 * DontCareFieldPosition defines no-op FieldDelegate. Its
 * singleton is used for the format methods that don't take a
 * FieldPosition.
 */
class DontCareFieldPosition extends FieldPosition {
    static final FieldPosition INSTANCE = new DontCareFieldPosition();

    private DontCareFieldPosition() {
        super(0);
    }

    @Override
    public void setBeginIndex(int bi) {
        //
    }

    @Override
    public void setEndIndex(int ei) {
        //
    }
}
