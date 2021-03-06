package com.nature.component.process.service.Impl;

import com.nature.base.util.LoggerUtil;
import com.nature.component.process.model.ProcessStop;
import com.nature.component.process.service.IProcessStopService;
import com.nature.component.process.utils.ProcessUtils;
import com.nature.component.process.vo.ProcessStopVo;
import com.nature.transaction.process.ProcessStopTransaction;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProcessStopServiceImpl implements IProcessStopService {

    Logger logger = LoggerUtil.getLogger();

    @Autowired
    private ProcessStopTransaction processStopTransaction;

    /**
     * Query processStop based on processId and pageId
     *
     * @param processId
     * @param pageId
     * @return
     */
    @Override
    public ProcessStopVo getProcessStopVoByPageId(String processId, String pageId) {
        ProcessStopVo processStopVo = null;
        ProcessStop processStopByPageId = processStopTransaction.getProcessStopByPageId(processId, pageId);
        if (null != processStopByPageId) {
            processStopVo = ProcessUtils.processStopPoToVo(processStopByPageId);
        }
        return processStopVo;
    }
}