package platform.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.api.model.CodeDto;
import platform.api.model.CodeUpdateResult;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/code")
public class ApiCodeController {
    private final CodeService codeService;

    public ApiCodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("latest")
    @ResponseBody
    public List<CodeDto> get() {
        return codeService.latest();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable UUID id) {
        final CodeDto code = codeService.findByIndex(id);
        if (code == null) {
            return ResponseEntity.notFound().build();
        }
        if (code.getViews() > 0) {
            code.setViews(code.getViews() -1);
        }
        return ResponseEntity.ok(code);
    }


    @PostMapping("/new")
    @ResponseBody
    public CodeUpdateResult newCode(final @RequestBody CodeDto code) {
        return codeService.update(code);
    }
}