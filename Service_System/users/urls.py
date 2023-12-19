from django.urls import path
from rest_framework_simplejwt.views import TokenObtainPairView

from users.views import SignUpAPIView

urlpatterns = [
    path("api/sign-up", SignUpAPIView.as_view()),
    path("api/sign-in", TokenObtainPairView.as_view()),
]
