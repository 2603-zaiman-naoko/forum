package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    CommentService commentService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    // @ModelAttribute：「画面から送られたデータをentityに詰め込んで」という意味
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // 7.追加実装課題(削除機能)
/*　自作
    @PostMapping("/del")
    public ModelAndView delContent(@ModelAttribute("contents") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.delReport(reportForm);
        // rootへリダイレクト→削除されていない？
        return new ModelAndView("redirect:/");
    }
*/
/* 回答 */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        // 削除したいidを渡す
        reportService.deleteReport(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // 7.追加実装課題(編集機能)
/*
    @PatchMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id) {
        // 更新したいidを渡す
        reportService.updateReport(id);
        // rootへリダイレクト
        return new ModelAndView("forward:/edit");
    }

↓以下回答*/
    /*
     * 編集画面表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        ReportForm report = reportService.editReport(id);
        // 編集する投稿をセット
        // editReportメソッドで取得した情報を詰める
        mav.addObject("formModel", report);
        // 画面遷移先を指定
        // フォワードで返却している（リダイレクトと記載が異なる）
        mav.setViewName("/edit");
        return mav;
    }

    /*
     * 編集処理
     */
    // @PutMapping：すでにあるデータを書き換える（update）を行うHTTPメソッド
    @PutMapping("/update/{id}")

    // @PathVariableで画面から受け取ったidをInteger idへ設定する
    // @ModelAttributeではid以外の情報（変更されたら困るもの以外）が設定されてくる
    public ModelAndView updateContent (@PathVariable Integer id,
                                       @ModelAttribute("formModel") ReportForm report) {
        // UrlParameterのidを更新するentityにセット
        // hiddenで渡されれば自動で設定される
        report.setId(id);
        // 編集した投稿を更新
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    // 1. 投稿へのコメント機能を追加
    @PostMapping("/addComments")
    public ModelAndView addComment(@PathVariable Integer id,
                                   @ModelAttribute("formModel") CommentForm commentForm){

        // URLから受け取ったので設定する
        commentForm.setId(id);

        // 返信をテーブルに格納
        commentService.saveComment(commentForm);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}