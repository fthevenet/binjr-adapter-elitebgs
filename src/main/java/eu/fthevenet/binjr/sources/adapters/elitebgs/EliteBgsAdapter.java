package eu.fthevenet.binjr.sources.adapters.elitebgs;

import eu.binjr.core.data.adapters.HttpDataAdapter;
import eu.binjr.core.data.adapters.TimeSeriesBinding;
import eu.binjr.core.data.codec.Decoder;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import org.eclipse.fx.ui.controls.tree.FilterableTreeItem;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;

public class EliteBgsAdapter extends HttpDataAdapter {
    public EliteBgsAdapter() throws CannotInitializeDataAdapterException {
    }

    public EliteBgsAdapter(URL baseAddress) throws CannotInitializeDataAdapterException {
        super(baseAddress);
    }

    @Override
    protected URI craftFetchUri(String path, Instant begin, Instant end) throws DataAdapterException {
        return null;
    }

    @Override
    public Decoder getDecoder() {
        return null;
    }

    @Override
    public FilterableTreeItem<TimeSeriesBinding> getBindingTree() throws DataAdapterException {
        return null;
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public ZoneId getTimeZoneId() {
        return null;
    }

    @Override
    public String getSourceName() {
        return null;
    }
}
