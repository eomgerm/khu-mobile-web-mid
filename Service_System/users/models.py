from django.contrib.auth.base_user import AbstractBaseUser
from django.contrib.auth.models import PermissionsMixin, BaseUserManager
from django.db import models


class UserManager(BaseUserManager):
    def create_user(self, email, password, username):
        if not email:
            raise ValueError("사용자 이메일이 없습니다.")
        user = self.model(email=email)
        user.set_password(password)
        user.username = username
        user.save(using=self._db)

        return user

    def create_superuser(self, email, password):
        superuser = self.create_user(email, password)

        superuser.is_superuser = True
        superuser.is_staff = True

        superuser.save(using=self._db)

        return superuser


class User(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(max_length=100, unique=True, null=False, blank=False)
    username = models.TextField(max_length=100, null=False, blank=False)
    is_superuser = models.BooleanField(default=False)
    is_staff = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    objects = UserManager()

    USERNAME_FIELD = 'email'


class FCMToken(models.Model):
    token_id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name="tokens")
    token = models.TextField(max_length=300, null=False, blank=False)
