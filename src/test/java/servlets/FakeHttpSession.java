package servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class FakeHttpSession implements HttpSession {
  private Map<String, Object> attributes = new LinkedHashMap<>();

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public void setMaxInactiveInterval(int i) {

  }

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    return null;
  }

  @Override
  public Object getAttribute(String attributeName) {
    return attributes.get(attributeName);
  }

  @Override
  public Object getValue(String s) {
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return null;
  }

  @Override
  public String[] getValueNames() {
    return new String[0];
  }

  @Override
  public void setAttribute(String attributeName, Object o) {
    attributes.put(attributeName, o);
  }

  @Override
  public void putValue(String s, Object o) {

  }

  @Override
  public void removeAttribute(String s) {

  }

  @Override
  public void removeValue(String s) {

  }

  @Override
  public void invalidate() {

  }

  @Override
  public boolean isNew() {
    return false;
  }

  public boolean hasAttributes(Map<String, Object> target) {
    return attributes.equals(target);
  }
}
