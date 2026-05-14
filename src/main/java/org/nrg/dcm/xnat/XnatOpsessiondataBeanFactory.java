package org.nrg.dcm.xnat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.dcm4che2.data.Tag;
import org.nrg.dcm.Attributes;
import org.nrg.dcm.DicomAttributeIndex;
import org.nrg.dcm.DicomMetadataStore;
import org.nrg.dcm.FixedDicomAttributeIndex;
import org.springframework.stereotype.Component;
import org.nrg.xdat.bean.XnatImagesessiondataBean;
import org.nrg.xdat.bean.XnatOpsessiondataBean;

/**
 *
 * @author jamesd
 * modified by Choon (SDIRP) for Op
 */
@Component
public class XnatOpsessiondataBeanFactory
        extends XnatImagesessiondataBeanFactory
{
    @Override
    public XnatImagesessiondataBean create(DicomMetadataStore store,
                                           String studyInstanceUid)
    {
        return create(store, studyInstanceUid, null);
    }

    @Override
    public XnatImagesessiondataBean create(DicomMetadataStore store,
                                           String studyInstanceUid, Map<String, String> parameters)
    {
        DicomAttributeIndex modalityAttr =
                new FixedDicomAttributeIndex(Tag.Modality);
        SetMultimap<DicomAttributeIndex,String> values =
                getValues(store, ImmutableMap.of(Attributes.StudyInstanceUID,
                        studyInstanceUid),
                        Collections.singleton(modalityAttr));
        if (values == null)
        {
            return null;
        }
        Set<String> modalities = values.get(modalityAttr);
        if (modalities != null && modalities.size() == 1 &&
                modalities.contains("OP"))
        {
            return new XnatOpsessiondataBean();
        }
        return null;
    }

}
