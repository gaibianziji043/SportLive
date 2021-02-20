package com.ft.base.http.upload;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class FileProgressRequestBody extends RequestBody {

    public interface ProgressListener {
        void transferred( long size );
    }

    public static final int SEGMENT_SIZE = 2*1024; // okio.Segment.SIZE

    protected File file;
    protected ProgressListener listener;

    public FileProgressRequestBody(File file,  ProgressListener listener) {
        this.file = file;
        this.listener = listener;
    }

    protected FileProgressRequestBody() {}

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            long total = 0;
            long read;

            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                total += read;
                sink.flush();
                this.listener.transferred(total);

            }
        } finally {
            Util.closeQuietly(source);
        }
    }

}
