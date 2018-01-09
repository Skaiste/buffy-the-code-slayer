package skaiste.API;

import com.khubla.antlr4example.ASTparser;
import com.khubla.antlr4example.SyntaxTree;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

public class CodeModel{
    private String language;
    private LocalDateTime timestamp;
    @DBRef
    private SyntaxTree code;
    private List<String> tags;
    private String originalCode;

    private int grade;
    private MatchingResult matchingResult;

    public CodeModel(String language, LocalDateTime timestamp, SyntaxTree code, String originalCode) {
        this.language = language;
        this.timestamp = timestamp;
        this.originalCode = originalCode;
        this.code = code;
        this.tags = code.getTags();
    }

    public CodeModel(String code) {
        // set original code
        originalCode = code;
        // set language
        language = "C";
        // set timestamp
        timestamp = LocalDateTime.now();
        // get the syntax tree & save it as code
        ASTparser parser = new ASTparser();
        try {
            this.code = parser.parseFromStringDeep(code);
            if (this.code != null)
                tags = this.code.getTags();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCodeValid() {
        if (code == null) return false;
        if (code.getTags() == null || code.getTags().size() == 0) return false;
        return !code.getTags().contains("ExternalDeclaration"); //?
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLanguage() {
        return language;
    }

    public SyntaxTree getCode() {
        return code;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public MatchingResult getMatch() {
        return matchingResult;
    }

    public void setMatchingResult(MatchingResult matchingResult) {
        this.matchingResult = matchingResult;
    }
}