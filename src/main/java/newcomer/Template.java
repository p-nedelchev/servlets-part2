package newcomer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Template {
    private final Map<String, String> placeHolderToValue = new LinkedHashMap<>();
    private final String templateValue;

    public Template(String templateValue) {
        this.templateValue = templateValue;
    }

    public void put(String placeHolder, String value) {
        placeHolderToValue.put(placeHolder, value);
    }

    public String evaluate() {
        String evaluationResult = templateValue;
        for (String placeHolder : placeHolderToValue.keySet()) {
            evaluationResult = evaluationResult.replaceAll("\\$\\{" + placeHolder + "\\}", placeHolderToValue.get(placeHolder));
        }
        return evaluationResult;
    }
}
