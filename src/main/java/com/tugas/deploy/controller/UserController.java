package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final String USERNAME = "admin";
    private final String PASSWORD = "20230140042";

    // List untuk menyimpan data mahasiswa (sementara, hilang saat restart)
    private static final List<User> daftarMahasiswa = new ArrayList<>();

    @GetMapping("/")
    public String loginpage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return "redirect:/home";   // Redirect agar tidak duplicate submit
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/home")
    public String homepage(Model model) {
        model.addAttribute("daftarMahasiswa", daftarMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String showInputForm(Model model) {
        model.addAttribute("user", new User());   // Kirim object kosong ke form
        return "form";
    }

    @PostMapping("/simpan")
    public String simpanData(@ModelAttribute("user") User user) {
        if (user.getNama() != null && user.getNim() != null) {
            daftarMahasiswa.add(user);
        }
        return "redirect:/home";   // Kembali ke home setelah simpan
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}