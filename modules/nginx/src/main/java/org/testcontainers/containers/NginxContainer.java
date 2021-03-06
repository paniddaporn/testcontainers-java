package org.testcontainers.containers;

import org.testcontainers.containers.traits.LinkableContainer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author richardnorth
 */
public class NginxContainer<SELF extends NginxContainer<SELF>> extends GenericContainer<SELF> implements LinkableContainer {

    private static final int NGINX_DEFAULT_PORT = 80;

    public NginxContainer() {
        super("nginx:1.9.4");
    }

    @Override
    protected Integer getLivenessCheckPort() {
        return getMappedPort(80);
    }

    @Override
    protected void configure() {
        addExposedPort(NGINX_DEFAULT_PORT);
        setCommand("nginx", "-g", "daemon off;");
    }

    public URL getBaseUrl(String scheme, int port) throws MalformedURLException {
        return new URL(scheme + "://" + getContainerIpAddress() + ":" + getMappedPort(port));
    }

    public void setCustomContent(String htmlContentPath) {
        addFileSystemBind(htmlContentPath, "/usr/share/nginx/html", BindMode.READ_ONLY);
    }

    public SELF withCustomContent(String htmlContentPath) {
        this.setCustomContent(htmlContentPath);
        return self();
    }
}
