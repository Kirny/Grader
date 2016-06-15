package framework.logging.serializers;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

import framework.logging.recorder.RecordingSession;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonSerializer implements RecordingSessionSerializer {
    @Override
    public String serialize(RecordingSession recordingSession) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, recordingSession);
            return writer.toString();
        } catch (IOException e) {
            System.out.println("Unable to write .json file:" + e.getMessage());
            return "{}";
        }
    }
}
