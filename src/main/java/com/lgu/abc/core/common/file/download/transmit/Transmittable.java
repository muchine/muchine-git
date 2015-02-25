package com.lgu.abc.core.common.file.download.transmit;

import java.io.IOException;
import java.io.OutputStream;

public interface Transmittable {

	void transmit(OutputStream stream) throws IOException;
	
}
