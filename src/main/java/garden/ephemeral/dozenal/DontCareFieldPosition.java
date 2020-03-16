package garden.ephemeral.dozenal;

import java.text.FieldPosition;

class DontCareFieldPosition extends FieldPosition {
    static final FieldPosition INSTANCE = new DontCareFieldPosition();

    DontCareFieldPosition() {
        super(0);
    }
}
