package pro.artse.fileserver.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import pro.artse.fileserver.errorhandling.ErrorHandler;

public final class Compressor {

	public static byte[] compress(byte[] data) throws IOException {
		try (ByteArrayOutputStream bao = new ByteArrayOutputStream()) {
			Deflater compressor = new Deflater();
			compressor.setInput(data);
			compressor.finish();
			byte[] readBuffer = new byte[1024];
			while (!compressor.finished()) {
				int readCount = compressor.deflate(readBuffer);
				bao.write(readBuffer, 0, readCount);
			}
			compressor.end();
			return bao.toByteArray();
		} catch (IOException e) {
			ErrorHandler.handle(e);
			throw e;
		}
	}

	public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
		try (ByteArrayOutputStream bao = new ByteArrayOutputStream()) {
			Inflater decompressor = new Inflater();
			decompressor.setInput(data);
			byte[] readBuffer = new byte[1024];
			while (!decompressor.finished()) {
				int readCount = decompressor.inflate(readBuffer);
				bao.write(readBuffer, 0, readCount);
			}
			decompressor.end();
			return bao.toByteArray();
		} catch (IOException e) {
			ErrorHandler.handle(e);
			throw e;
		}
	}
}
