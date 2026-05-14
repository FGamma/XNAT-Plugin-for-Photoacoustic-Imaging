package org.nrg.dcm.xnat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import java.util.Collections;
import java.util.Set;
import org.dcm4che2.data.Tag;
import org.nrg.dcm.Attributes;
import org.nrg.dcm.DicomAttributeIndex;
import org.nrg.dcm.DicomMetadataStore;
import org.nrg.dcm.FixedDicomAttributeIndex;
import org.nrg.xdat.bean.XnatImagescandataBean;
import org.nrg.xdat.bean.XnatPhotoacousticimagingscandataBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author jamesd
 * modified by Choon (SDIRP) for Photoacousticimaging
 */
@Component
public class XnatPhotoacousticimagingscandataBeanFactory extends XnatImagescandataBeanFactory
{
    @Override
    public XnatImagescandataBean create(Series series, DicomMetadataStore store)
    {
        DicomAttributeIndex modalityAttr =
                new FixedDicomAttributeIndex(Tag.Modality);
        SetMultimap<DicomAttributeIndex,String> values = getValues(store,
                ImmutableMap.of(Attributes.SeriesInstanceUID, series.getUID()),
                Collections.singleton(modalityAttr));
        if (null == values)
        {
            return null;
        }
        Set<String> modalities = values.get(modalityAttr);
        if (modalities != null && modalities.size() == 1 &&
                modalities.contains("PAI"))
        {
            return new XnatPhotoacousticimagingscandataBean();
        }
        return null;
    }

}
