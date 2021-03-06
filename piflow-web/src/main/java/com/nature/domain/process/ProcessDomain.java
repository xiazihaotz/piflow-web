package com.nature.domain.process;

import com.nature.base.util.SessionUserUtil;
import com.nature.base.vo.UserVo;
import com.nature.component.process.model.Process;
import com.nature.repository.process.ProcessJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ProcessDomain {

    @Autowired
    private ProcessJpaRepository processJpaRepository;

    private Specification<Process> addEnableFlagParam() {
        Specification<Process> specification = new Specification<Process>() {
            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //Root.get("enableFlag") means to get the name of the enableFlag field.
                return criteriaBuilder.equal(root.get("enableFlag"), 1);
            }
        };
        return specification;
    }

    private Specification<Process> addParam(String key, String value) {
        Specification<Process> specification = new Specification<Process>() {
            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //Root.get(key) means to get the name of the key field.
                return criteriaBuilder.equal(root.get(key), value);
            }
        };
        return specification;
    }

    public Process getProcessById(String id) {
        Process process = processJpaRepository.getOne(id);
        if (null != process && !process.getEnableFlag()) {
            process = null;
        }
        return process;
    }

    public Page<Process> getProcessListPage(int page, int size, String param) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "crtDttm"));
        boolean isAdmin = SessionUserUtil.isAdmin();
        if (isAdmin) {
            return processJpaRepository.getProcessListPage(null == param ? "" : param, pageRequest);
        } else {
            UserVo currentUser = SessionUserUtil.getCurrentUser();
            return processJpaRepository.getProcessListPage(currentUser.getUsername(), null == param ? "" : param, pageRequest);
        }
    }

    public Process saveOrUpdate(Process process) {
        return processJpaRepository.save(process);
    }

    public List<Process> saveOrUpdate(List<Process> processList) {
        return processJpaRepository.saveAll(processList);
    }

    public int updateEnableFlagById(String id, boolean enableFlag) {
        return processJpaRepository.updateEnableFlagById(id, enableFlag);
    }

    public Process getProcessByPageId(String fid, String pageId) {
        return processJpaRepository.getProcessByPageId(fid, pageId);
    }

    public String getProcessIdByNameAndProcessGroupId(String fid, String processName) {
        return processJpaRepository.getProcessIdByNameAndProcessGroupId(fid, processName);
    }

    public String getMaxStopPageIdByProcessGroupId(String processGroupId) {
        return processJpaRepository.getMaxStopPageIdByProcessGroupId(processGroupId);
    }


}
